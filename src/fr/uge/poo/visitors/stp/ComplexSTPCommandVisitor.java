package fr.uge.poo.visitors.stp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.evilcorp.stp.*;

public class ComplexSTPCommandVisitor implements STPCommandVisitor {
	
	private HashMap<Integer,Long> timers = new HashMap<>();
	private ArrayList<Observer> observers = new ArrayList<>();
	
	public ComplexSTPCommandVisitor() {
		observers.add(new CommandsObserver());
		observers.add(new StatObserver());
	}
	
	private interface Observer {
		
		public void onCommandHello();
		public void onCommandStart(int id);
		public void onCommandStop(int id);
		public void onCommandElapsed();
		public void onCommandExit();
		
	}
	
	private class CommandsObserver implements Observer {
		
		private HashMap<String, Integer> commands = new HashMap<>();
		
		public CommandsObserver() {
			for (var command : Arrays.asList("hello", "stop", "start", "elapsed", "exit")) {
				register(command);
			}
		}

		private void register(String command) {
			if (!commands.containsKey(command)) {
				commands.put(command, 0);
			}			
		}
		
		private void increment(String command) {
			commands.put(command, commands.get(command) + 1);
		}
		
		@Override
		public void onCommandHello() {
			increment("hello");
		}

		@Override
		public void onCommandStart(int id) {
			increment("start");
		}

		@Override
		public void onCommandStop(int id) {
			increment("stop");
		}

		@Override
		public void onCommandElapsed() {
			increment("elapsed");
		}

		@Override
		public void onCommandExit() {
			increment("exit");
			var stringBuilder = new StringBuilder();
			for (var entry : commands.entrySet()) {
				stringBuilder.append(entry.getKey());
				stringBuilder.append(" : ");
				stringBuilder.append(entry.getValue());
				stringBuilder.append("\n");
			}
			System.out.println(stringBuilder.toString());
		}
	    
	}
	
	private class StatObserver implements Observer {
		
		private HashMap<Integer,Long> timers = new HashMap<>();
		private ArrayList<Long> durations = new ArrayList<>();
		
		@Override
		public void onCommandHello() {
			// nothing
		}

		@Override
		public void onCommandStart(int id) {
	        if (!(timers.get(id) != null)) {
	        	timers.put(id, System.currentTimeMillis());
	        }
		}

		@Override
		public void onCommandStop(int id) {
			var startTime = timers.get(id);
	        if (!(startTime == null)) {
	        	durations.add(System.currentTimeMillis() - startTime);
		        timers.put(id, null);
	        }
		}

		@Override
		public void onCommandElapsed() {
			// nothing
		}

		@Override
		public void onCommandExit() {
			if (!(durations.isEmpty())) {
				var average = durations.stream().mapToLong(x -> x).average().getAsDouble();
				System.out.println("Average of execution time: " + average + " ms.");
			}
		}
	    
	}
	
	@Override
	public void visit(HelloCmd zip) {
		observers.forEach(observer -> observer.onCommandHello());
		System.out.println("Hello the current date is " + LocalDateTime.now());
	}

	@Override
	public void visit(StartTimerCmd cmd) {
		var timerId = cmd.getTimerId();
		observers.forEach(observer -> observer.onCommandStart(timerId));        
        if (timers.get(timerId) != null){
            System.out.println("Timer " + timerId + " was already started");
            return;
        }
        var currentTime = System.currentTimeMillis();
        timers.put(timerId, currentTime);
        System.out.println("Timer " + timerId + " started");
	}

	@Override
	public void visit(StopTimerCmd cmd) {
		var timerId = cmd.getTimerId();
		observers.forEach(observer -> observer.onCommandStop(timerId));        
        var startTime = timers.get(timerId);
        if (startTime == null) {
            System.out.println("Timer " + timerId + " was never started");
            return;
        }
        var currentTime = System.currentTimeMillis();
        System.out.println("Timer " + timerId + " was stopped after running for " + (currentTime - startTime) + "ms");
        timers.put(timerId, null);
	}
	
	@Override
	public void visit(ElapsedTimeCmd cmd) {
		observers.forEach(observer -> observer.onCommandElapsed());
        var currentTime =  System.currentTimeMillis();
        for(var timerId : cmd.getTimers()){
            var startTime = timers.get(timerId);
            if (startTime == null){
                System.out.println("Unknown timer " + timerId);
                return;
            }
            System.out.println("Ellapsed time on timerId " + timerId + " : " + (currentTime - startTime) + "ms");
        }
	}

	@Override
	public void visit(ExitCmd exitCmd) {
		observers.forEach(observer -> observer.onCommandExit());
		System.exit(0);
	}

}
