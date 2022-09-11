# Clojure Monorepo Sandbox

Project to demonstrate an approaches around organizing Clojure libraries

Currently contains 3 main subfolders:
1. `/shared` libraries that work both in Browsers and on Node.js
2. `/server` libraries meant only for Node.js
3. `/browser` libraries meant only for in browser use

## Useful babashka references

Help and useful materials:
  - Filesystem API: https://github.com/babashka/fs/blob/master/API.md
  - Simple HTTP requests: https://github.com/babashka/babashka.curl#get
  - Interacting with command line tools: https://book.babashka.org/#_clojure_java_shell
   - (clojure.java.shell/sh "ls" "-la")
