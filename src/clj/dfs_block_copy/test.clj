(ns dist-copy.test
  (:use [clojure.java.io :as io])
  (:import [org.apache.hadoop.conf Configuration]
           [org.apache.hadoop.fs Path FileSystem]
           ;           [org.apache.hadoop.hdfs MiniDFSCluster]
           [org.apache.hadoop.yarn.server MiniYARNCluster]))

;; (defn create-dfs-cluster [^Configuration conf]
;;   (-> conf
;;       org.apache.hadoop.hdfs.MiniDFSCluster$Builder.
;;       (.numDataNodes 8)
;;       .build))

;; (defn- destroy-dfs-cluster [cluster]
;;   (when (.isClusterUp cluster)
;;     (.shutdown cluster)))

(defn write-to [^Configuration conf path amt]
  (let [fs (FileSystem/get conf)
        data (byte-array amt)
        path (Path. path)]
    (with-open [out (.create fs path)]
      (io/copy data out))))

(defn create-yarn-cluster [^Configuration conf]
  (doto (MiniYARNCluster. "testing" 1 1 1)
    (.init conf)
    (.start)))

(defn destroy-yarn-cluster [cluster]
  (when (not (nil? cluster))
    (.stop cluster)))