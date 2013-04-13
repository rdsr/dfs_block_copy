(ns dfs_block_copy.core
  (:require [clojure.java.io :as io])
  (:import [dfs_block_copy.util MultiPathsInputStream]
           [org.apache.hadoop.conf Configuration]
           [org.apache.hadoop.fs Path FileSystem]))

(defn file-complete? [conf dir name sz src-blck-sz]
  (let [fs (FileSystem/get conf)]
    (every? (fn [offset]
              (.exists fs (Path. dir (str name "__" offset))))
            (take-while #(< % sz) (iterate #(+ % src-blck-sz) 0)))))

(defn make-paths [dir name sz src-blck-sz]
  (map (fn [offset]
         (Path. dir (str name "__" offset)))
       (take-while #(< % sz) (iterate #(+ % src-blck-sz) 0))))

(defn re-construct-file [conf dir name sz src-blck-sz]
  (let [paths (make-paths dir name sz src-blck-sz)
        fs (FileSystem/get conf)]
    (with-open [is (MultiPathsInputStream. conf paths)
                os (.open fs (Path. dir name))]
      (io/copy is os))))
