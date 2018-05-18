package com.lightbend.akka.reactive;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * https://dzone.com/refcardz/reactive-programming-akka?chapter=1
 */
public class Application {

    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create();

        final ActorRef botMaster = system.actorOf(
                Props.create(BotMaster.class),
                "botMaster");

        botMaster.tell(new BotMaster.StartChildBots(), ActorRef.noSender());

        System.out.println("Press any key to terminate");
        System.in.read();
        System.out.println("Shutting down actor system...");
        system.terminate();
    }

}

