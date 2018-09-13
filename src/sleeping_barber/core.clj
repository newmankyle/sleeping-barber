(ns sleeping-barber.core
  (:gen-class))

;;In this section, I’m going to outline a single problem called sleeping
;;barber. It was created by Edsger Dijkstra in 1965. It has these characteristics:
;;• A barber shop takes customers.
;;• Customers arrive at random intervals, from ten to thirty milliseconds.
;;• The barber shop has three chairs in the waiting room.
;;• The barber shop has one barber and one barber chair.
;;• When the barber’s chair is empty, a customer sits in the chair,
;;      wakes up the barber, and gets a haircut.
;;• If the chairs are occupied, all new customers will turn away.
;;• Haircuts take twenty milliseconds.
;;• After a customer receives a haircut, he gets up and leaves.
;;Write a multithreaded program to determine how many haircuts a barber
;;can give in ten seconds.

;;Queue methods:
;; peek: into the dt
;; pop: get first piece of data
;; conj: append the queue with new data

(def started (System/currentTimeMillis))

(def barber-chair (atom :empty))
(def haircut-count (agent 0))



(defn time-elapsed [] 
  (- (System/currentTimeMillis) started))

(defn queue [] 
  (atom
    (clojure.lang.PersistentQueue/EMPTY)))

(def chairs (queue))

(defn add! [element] 
  (swap! chairs conj element))

(defn get-waitlist! [] 
  (count @chairs))

(defn serve! []
  (swap! chairs pop))

(defn give-haircut! [] 
  (serve!)
  ;(println "gave haircut")
  (Thread/sleep 20)
  (send haircut-count inc))

(defn still-customers []
  (not (empty? @chairs)))

(defn close-down! [] 
  (reset! barber-chair :closed)
  (println "ending barber"))

(defn barber []
  (future
    (println "beginning barber")
      (while (<= (time-elapsed) 10000) 
        (do
          (if (still-customers) (give-haircut!))))
    (println "customers are done")
    (close-down!)))

(defn customer-time []
  (+ (rand-int 20) 10))

(defn fill-chair []
  (Thread/sleep (customer-time))
  ;(println "another customer has entered the building")
  (add! :customer))

(defn customers [] 
  (future
    (println "beginning customer")
      (while (not (= @barber-chair :closed))
        (do
          (if (not (>= (get-waitlist!) 3))
            (fill-chair))))
      (println "exited")))

(defn -main [& args]
    ;(add-watch barber-chair :print #(println "Barber has taken another customer"))
    (println "starting barber")
    (barber)
    (println "starting customers")
    (customers)
    (Thread/sleep 20000)
    (println "we served: " @haircut-count)
    (println "closing up")
    (shutdown-agents))
