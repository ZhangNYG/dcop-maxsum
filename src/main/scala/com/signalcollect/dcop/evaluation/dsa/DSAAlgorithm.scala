package com.signalcollect.dcop.evaluation.dsa

import com.signalcollect.dcop.benchmark.BenchmarkModes
import com.signalcollect.dcop.benchmark.BenchmarkConfiguration
import com.signalcollect.dcop.evaluation.candidates.DSAVariant
import com.signalcollect.configuration.ExecutionMode

class DSAAlgorithm(config : BenchmarkConfiguration, dsaVariant : DSAVariant.Value, pSchedule : Double, graphSize : Int) {

    private val configuration = config
    
  
  /*
   * measurements
   */
  private var conflictsOverTime : List[Tuple2[Int,Int]] = List()
  private var resultingConflicts : Int = 0
  private var stepsToConvergence : Int = 0
  private var timeToConvergence : Long = 0
  
  //an executable instance of the algorithm
  val algorithm : DSAExecutor = new DSAExecutor(configuration.file, configuration.executionConfiguration, configuration.numOfColors, configuration.isAdopt, configuration.aggregationOperation, dsaVariant, pSchedule, graphSize)
  
  def runEvaluation() = {
    configuration.mode match {
      case BenchmarkModes.SyncConflictsOverTime => evaluateSyncConflictsOverTime()
      case BenchmarkModes.AsyncResultingConflicts => evaluateAsyncResultingConflicts()
      case BenchmarkModes.SyncResultingConflicts => evaluateSyncResultingConflicts()
      case BenchmarkModes.SyncStepsToConvergence =>evaluateSyncStepsToConvergence()
      case BenchmarkModes.AsyncTimeToConvergence => evaluateAsyncTimeToConvergence()
      case BenchmarkModes.SyncTimeToConvergence => evaluateSyncTimeToConvergence()
      case _ => println("Unknown BenchmarkMode. Exiting.."); System.exit(-1)
    }
  }
  
    def getResult() = {
    configuration.mode match {
      case BenchmarkModes.SyncConflictsOverTime => conflictsOverTime
      case BenchmarkModes.AsyncResultingConflicts => resultingConflicts
      case BenchmarkModes.SyncResultingConflicts => resultingConflicts
      case BenchmarkModes.SyncStepsToConvergence => stepsToConvergence
      case BenchmarkModes.AsyncTimeToConvergence => timeToConvergence
      case BenchmarkModes.SyncTimeToConvergence => timeToConvergence
      case _ => println("Unknown BenchmarkMode. Exiting.."); System.exit(-1)
    }
  }
  
  private def evaluateSyncConflictsOverTime() = {
    if(configuration.executionConfiguration.executionMode != ExecutionMode.Synchronous){
      println("ERROR: Can't evaluate ConflictsOverTime on Asynchronous BenchmarkConfiguration.")
      println("Exiting...")
      System.exit(-1)
    }else{
    	for(run <- 1 to configuration.stepsLimit){
    		val partialResult = algorithm.executeWithAggregation()
    		if(partialResult == -1){
    		  println("ERROR: executeWithAggregation failed, AggregationOperation was null")
    		  System.exit(-1)
    		}else{
    		  conflictsOverTime :+= (run,partialResult)
    		}
    	}
    }
  }
  
  private def evaluateSyncResultingConflicts() = {
    if(configuration.executionConfiguration.executionMode != ExecutionMode.Synchronous){
      println("ERROR: Can't evaluate SyncResultingConflicts on Asynchronous BenchmarkConfiguration.")
      println("Exiting...")
      System.exit(-1)
    }else{
      //TODO: analyze sync
    }
  }
  
  private def evaluateAsyncResultingConflicts() {
    if(configuration.executionConfiguration.executionMode == ExecutionMode.Synchronous){
      println("ERROR: Can't evaluate AsyncResultingConflicts on Synchronous BenchmarkConfiguration.")
      println("Exiting...")
      System.exit(-1) 
    }else{
      //TODO: analyze async
    }
  }
  
  private def evaluateSyncStepsToConvergence() = {
    if(configuration.executionConfiguration.executionMode != ExecutionMode.Synchronous){
      println("ERROR: Can't evaluate StepsToConvergence on Asynchronous BenchmarkConfiguration.")
      println("Exiting...")
      System.exit(-1)
    }else{
      //TODO: analyze sync
    }
  }
  
  private def evaluateSyncTimeToConvergence() = {
    if(configuration.executionConfiguration.executionMode != ExecutionMode.Synchronous){
      println("ERROR: Can't evaluate SyncTimeToConvergence on Asynchronous BenchmarkConfiguration.")
      println("Exiting...")
      System.exit(-1) 
    }else{
      //TODO: analyze sync
    }
  }
  
  private def evaluateAsyncTimeToConvergence() = {
    if(configuration.executionConfiguration.executionMode == ExecutionMode.Synchronous){
      println("ERROR: Can't evaluate AsyncTimeToConvergence on Synchronous BenchmarkConfiguration.")
      println("Exiting...")
      System.exit(-1)  
    }else{
      //TODO: analyze async
    }
  }
}