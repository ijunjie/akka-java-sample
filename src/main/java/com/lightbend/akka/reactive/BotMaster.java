package com.lightbend.akka.reactive;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;

import java.util.stream.IntStream;

public class BotMaster extends AbstractActor {

    public BotMaster() {
        IntStream.range(0, 10)
                .forEach(i -> {
                    final ActorRef child = getContext().actorOf(Props.create(AkkaBot.class));
                    getContext().watch(child);
                });
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StartChildBots.class, this::onStartChildBots)
                .match(Terminated.class, this::onChildTerminated)
                .build();
    }

    private void onStartChildBots(StartChildBots startChildBots) {
        final AkkaBot.Move move =
                new AkkaBot.Move(AkkaBot.Direction.FORWARD);
        getContext().getChildren().forEach(child -> {
            //System.out.println("Master started moving " + child);
            child.tell(move, getSelf());
        });
    }

    private void onChildTerminated(Terminated terminated) {
        System.out.println(getSender().path() + " has stopped, starting a new one");
        final ActorRef child =
                getContext().actorOf(Props.create(AkkaBot.class));
        getContext().watch(child);
    }


    public static class StartChildBots {
    }
}
