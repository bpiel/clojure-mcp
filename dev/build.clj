(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'clojure-mcp/clojure-mcp)
(def version "0.1.0")
(def basis (b/create-basis {:project "deps.edn" :aliases [:uberjar]}))
(def class-dir "target/classes")
(def uber-file "target/clojure-mcp-0.1.0-standalone.jar")

(defn clean [_]
  (b/delete {:path "target"}))

(defn uberjar [_]
  (clean nil)
  (b/copy-dir {:src-dirs ["src" "dev"]
               :target-dir class-dir})
  (b/copy-dir {:src-dirs ["resources"] :target-dir class-dir})
  (b/compile-clj {:basis basis
                  :src-dirs ["src" "dev"]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis basis
           :main 'clojure-mcp.sse-main}))
