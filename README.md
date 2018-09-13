# sleeping_barber

An implementation of the sleeping barber problem. I'm using this problem to introduce myself to clojure after reading *Seven Languages in Seven Weeks*. The description is provided below (from page 263):

    In this section, I’m going to outline a single problem called sleeping
    barber. It was created by Edsger Dijkstra in 1965. It has these characteristics:
    * A barber shop takes customers.
    * Customers arrive at random intervals, from ten to thirty milliseconds.
    * The barber shop has three chairs in the waiting room.
    * The barber shop has one barber and one barber chair.
    * When the barber’s chair is empty, a customer sits in the chair,
     wakes up the barber, and gets a haircut.
    * If the chairs are occupied, all new customers will turn away.
    * Haircuts take twenty milliseconds.
    * After a customer receives a haircut, he gets up and leaves.
    Write a multithreaded program to determine how many haircuts a barber
    can give in ten seconds.

## Usage

    $ lein run

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
