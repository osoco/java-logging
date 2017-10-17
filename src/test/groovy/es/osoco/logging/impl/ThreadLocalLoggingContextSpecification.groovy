package es.osoco.logging.impl

import es.osoco.logging.LoggingContext
import spock.lang.Specification

class ThreadLocalLoggingContextSpecification
        extends Specification {

    def "A LoggingContext retrieves a value already stored in the thread"() {
        setup:
        LoggingContext context = new ThreadLocalLoggingContext()
        Date date = new Date()
        context.put("a key", date)

        expect:
        context.get("a key") == date
    }

    class MyRunnable
        implements Runnable {
        LoggingContext context
        Date dateStored
        Date dateRetrieved
        MyRunnable(LoggingContext ctx, Date date) {
            this.context = ctx
            this.dateStored = date
        }
        @Override
        void run() {
            context.put("date", dateStored)
            sleep(1000)
            this.dateRetrieved = context.get("date")
            println this.dateRetrieved
        }
    }

    def "A LoggingContext does not retrieve a value stored by another thread"() {
        setup:
        LoggingContext context = new ThreadLocalLoggingContext()
        Date date1 = new Date()
        Date date2 = date1.plus(3)
        Runnable run1 = new MyRunnable(context, date1)
        Runnable run2 = new MyRunnable(context, date2)
        Thread thread1 = new Thread(run1)
        Thread thread2 = new Thread(run2)
        thread1.start()
        thread2.start()

        while (thread1.isAlive() || thread2.isAlive()) {
            Thread.sleep(1000)
        }

        expect:
        run1.dateStored == run1.dateRetrieved
        run2.dateStored == run2.dateRetrieved
        run1.dateRetrieved != run2.dateRetrieved
    }
}
