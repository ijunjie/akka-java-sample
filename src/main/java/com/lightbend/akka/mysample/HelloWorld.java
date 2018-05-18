package com.lightbend.akka.mysample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

/**
 * Hello world!
 */
public class HelloWorld extends AbstractActor {

    final ActorRef greeterActor;

    public HelloWorld(ActorRef greeter) {
        this.greeterActor = greeter;
    }
//    @Override
//    public void preStart() throws Exception {
//        greeterActor = getContext().actorOf(Props.create(Greeter.class), "greeter");
//        System.out.println("Greeter Actor Path:" + greeterActor.path());
//        greeterActor.tell(Greeter.Msg.GREET, getSelf());
//    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(Greeter.Msg.DONE, msg -> {
                    greeterActor.tell(Greeter.Msg.GREET, getSelf());
                    getContext().stop(getSelf());
                })
                .build();
    }

}
