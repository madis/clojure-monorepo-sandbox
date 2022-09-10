#!/usr/bin/env bb

(ns migrate-library
  (:require [clojure.java.shell :refer [sh with-sh-dir]]
            [babashka.fs :as fs]))

; Help and useful materials:
;  Filesystem API: https://github.com/babashka/fs/blob/master/API.md
;  Simple HTTP requests: https://github.com/babashka/babashka.curl#get
;  Interacting with command line tools: https://book.babashka.org/#_clojure_java_shell
;    - (clojure.java.shell/sh "ls" "-la")

(defn absolutize-path [path]
  (let [absolute-path? (clojure.string/starts-with? "/" path)]
  (if absolute-path? path (str (fs/cwd) "/" path))))

(defn library-structure-as-expected? [library-path]
  (let [absolute-path (absolutize-path library-path)
        using-deps? (fs/exists? (str absolute-path "/deps.edn"))]
    (and using-deps?)))

(defn move-merging-git-histories [source-path target-path]
  (let [remote-name (str (last (fs/components source-path)))
        _ (sh "git" "reset" "--hard" :dir target-path) ; To avoid 'Working tree has modifications' error
        ; Idea from https://www.jvt.me/posts/2018/06/01/git-subtree-monorepo/
        merge-result (sh "git" "subtree" "add" (str "--prefix=" remote-name) source-path "master" :dir target-path)]
    (if (= 1 (:exit merge-result))
      (throw (ex-info (str "Failed running `git subtree`: " (:err merge-result)) merge-result)))))

(defn -main [& args]
  (let [library-path (first *command-line-args*)]
    (println "Working with:" library-path)
    (println "library-structure-as-expected?" (library-structure-as-expected? library-path))))

; To allow running as commandn line util but also required & used in other programs or REPL
; https://book.babashka.org/#main_file
(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
