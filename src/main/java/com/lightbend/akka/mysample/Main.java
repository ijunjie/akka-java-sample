package com.lightbend.akka.mysample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("Hello");
        ActorRef greeterActor = system.actorOf(Props.create(Greeter.class, () -> new Greeter()), "greeterActor");
        ActorRef helloWorld = system.actorOf(Props.create(HelloWorld.class, () -> new HelloWorld(greeterActor)),
                "helloWorldActor");
        System.out.println("greeter path: " + greeterActor.path());
        System.out.println("hello world path: " + helloWorld.path());
        System.out.println("---------");

        greeterActor.tell(Greeter.Msg.GREET, helloWorld);

        system.terminate();
    }
}
