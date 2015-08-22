(defproject clojure-parallel-dirsize "0.1"
  :description "Example of parallel dirsize computation."
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main com.jpi.clojure.pdirsize
  :plugins [[lein-bin "0.3.4"]]
  :bin { :name "pdirsize"
         :bin-path "./bin" }
  )
