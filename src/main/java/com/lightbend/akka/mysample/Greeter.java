package com.lightbend.akka.mysample;

import akka.actor.AbstractActor;

public class Greeter extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(Msg.GREET, msg -> {
                    getSender().tell(Msg.DONE, getSelf());
                })
                .build();
    }

    public static enum Msg {
        GREET, DONE
    }


}
