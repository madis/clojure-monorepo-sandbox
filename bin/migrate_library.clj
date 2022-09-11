#!/usr/bin/env bb

(ns migrate-library
  (:require [clojure.java.shell :refer [sh with-sh-dir]]
            [babashka.fs :as fs]))

(defn log [& args]
  (apply println args))

(defn absolutize-path [path]
  (let [absolute-path? (clojure.string/starts-with? "/" path)]
  (if absolute-path? path (str (fs/cwd) "/" path))))

(defn library-structure-as-expected? [library-path]
  (let [absolute-path (absolutize-path library-path)
        using-deps? (fs/exists? (str absolute-path "/deps.edn"))]
    (and using-deps?)))

(defn move-merging-git-histories [source-path target-path]
  (let [remote-name (str (last (fs/components source-path)))
        ; _ (sh "git" "reset" "--hard" :dir target-path) ; To avoid 'Working tree has modifications' error
        _ (sh "git" "stash" :dir target-path) ; Remember any changes in the working tree
        ; Idea from https://www.jvt.me/posts/2018/06/01/git-subtree-monorepo/
        merge-result (sh "git" "subtree" "add" (str "--prefix=" remote-name) source-path "master" :dir target-path)
        _ (sh "git" "stash" "pop" :dir target-path) ; Restore the changes in the working tree
        ]
    (if (= 1 (:exit merge-result))
      (throw (ex-info (str "Failed running `git subtree`: " (:err merge-result)) merge-result)))))

(defn -main [& args]
  (println "WITH ARGS: " args)
  (let [[library-path target-path] *command-line-args*]
    (log "Migrating" library-path "to" target-path)
    (cond
      (library-structure-as-expected? library-path) (move-merging-git-histories library-path target-path)
      :else (log "Library doesn't have the expected structure (needs deps.edn)"))
    (move-merging-git-histories library-path target-path)))

; To allow running as commandn line util but also required & used in other programs or REPL
; https://book.babashka.org/#main_file
(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))
