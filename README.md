# clojure-parallel-dirsize

Demonstration of agent system that computes directory size in parallel manner.
Producer/Consumer pattern has been used to create list of directories (Producer) and then sum up the sizes of all leaf nodes / files (Consumer)

Used IDEA 14 with [Cursive plugin] (https://cursiveclojure.com/userguide/index.html)
[Leiningen 2.5.x] (https://github.com/technomancy/leiningen/wiki/Packaging)
Clojure 1.6.0

Create executable
```
lein bin
```

## Usage
```
./pdirsize <path>
```
i.e.
```
Mac-Daddy:bin jan$ ./pdirsize /Volumes/DEV/
Computing size recursively for > /Volumes/DEV/
Total size > 47883443025B
```

##Future work [not planned]
1. make the throughput adjustable (more producers/consumers)
2. ...

## License

Copyright © 2011 jan.palencar

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
