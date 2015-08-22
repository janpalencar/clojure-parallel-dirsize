(ns com.jpi.clojure.pdirsize
  (:gen-class))

(def run? (atom true))
(def ^:dynamic *max-queue-length* 1024)
(def ^:dynamic *max-wait-time* 10)    ;; wait max 10 milisecond then process anything left
(def ^:dynamic *chunk-size* 64)
(declare ^:dynamic a)
(def queue (java.util.concurrent.LinkedBlockingQueue. *max-queue-length* ))
(def agents (atom []))
(def size-total (atom 0))

(defn file-cur [path]
  (clojure.java.io/file path))

(defn branch-producer [node]
  (if @run?
    (doseq [f node]
      (when (.isDirectory f)
        (do (.put queue f)
            (branch-producer (.listFiles f)))))))

(defn producer [node]
  (future
    (branch-producer node)))

(defn node-consumer [node]
  (if (.isFile node)
    (.length node)
    0))

(defn chunk-length []
  (min (.size queue) *chunk-size*))

(defn compute-sizes [a]
  (doseq [i (map (fn [f] (.listFiles f)) a)]
    (swap! size-total #(+ % (apply + (map node-consumer i))))))

(defn consumer []
  (future
    (while @run?
      (when-let [size (if (zero? (chunk-length))
                        false
                        (chunk-length))] ;appropriate size of work
        (binding [a (agent [])]
          (dotimes [_ size]         ;give us all directories to process
            (when-let [item (.poll queue)]
              (set! a (agent (conj @a item)))))
          (swap! agents #(conj % a))
          (send-off a compute-sizes))
        (Thread/sleep *max-wait-time*)))))

;; We stop when @size-total has not changed after 500ms
(defn -main [& args]
  (.println (System/out) (str "Computing size recursively for > " (first args)))
  (producer (list (file-cur (first args))))
  (consumer)
  (while @run?
     (let [startSize @size-total]
      (Thread/sleep 500)
      (if (= startSize @size-total)
        (swap! run? not))))
  (.println (System/out) (str "Total size > " @size-total "B" ))
  (System/exit 0))

