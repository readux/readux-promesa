# readux-promesa

A middleware function implementing automatic resolution and dispatching
of [promesa](https://github.com/funcool/promesa)-based promises.

```
#(rdc/dispatch 
  store
  :fetch-posts
  [(p/promise (fn [done err](err 20))) "something" "else"])

;; Dispatches :fetch-posts-rq
;;   data: {:data ("something" "else")}
;; ... when promise is resolved
;; Dispatches :fetch-posts-error
;;   data:  {:data ("something" "else")
;;           :payload nil
;;           :error 20}
```

```
#(rdc/dispatch 
  store
  :fetch-posts
  [(p/promise (fn [done err](done 10))) "something"])

;; Dispatches :fetch-posts-rq
;;   data: {:data ("something" "else")}
;; ... when promise is resolved
;; Dispatches :fetch-posts-success
;;   data:  {:data ("something")
;;           :payload 10}
```

## readux
A ClojureScript library to facilitate predictable state state management for
your reagent-based web-apps. Conceptually, Readux is a reimplementation of the
[redux](http://redux.js.org) library.

Please see [the docs](https://readux.github.io) for more information.

## License

Copyright © 2016 Jesper Wendel Devantier

Distributed under the MIT License. By submitting a pull request for
this project, you agree to license your contribution under the MIT
license to this project.