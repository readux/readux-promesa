(ns readux-promesa.core
  (:require [promesa.core :as p]))

(defn- keyword-append
  "Construct a keyword by appending an arbitrary sequence of strings/keywords"
  [& args]
  (->> args
       (map name)
       (apply str)
       keyword))

(defn mw-promesa
  [dispatch next model action data]
  (let [[p & rest] data
        rsp (if rest {:data rest} {})]
    (if (p/promise? p)
      (do (let [on-success (keyword-append action ".success")
                on-error (keyword-append action ".error")]
            (dispatch (keyword-append action ".rq") rsp)
            (-> p
                (p/then #(dispatch on-success (assoc rsp :payload %)))
                (p/catch #(dispatch on-error (assoc rsp :payload nil
                                                        :error %)))))
          model)
      (next model action data))))