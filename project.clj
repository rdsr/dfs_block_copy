(defproject dfs_block_copy "0.1.0-SNAPSHOT"
  :source-paths ["src/clj"]
  :java-source-paths ["src/jvm"]
  :resource-paths ["conf"]
  :description "inter and intra copy of hdfs blocks"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.apache.hadoop/hadoop-common "0.23.6"]
                 [org.apache.hadoop/hadoop-hdfs "0.23.6"]
                 [org.apache.hadoop/hadoop-mapreduce-client-core "0.23.6"]
                 [org.apache.hadoop/hadoop-mapreduce-client-common "0.23.6"]
                 [org.apache.hadoop/hadoop-yarn-common "0.23.6"]])
