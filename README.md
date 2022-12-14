# Predict Number

+ A **game** that allows the **user** to predict the **number** between certain
  **bounds**.
+ The number has to be predicted within certain **attempts**.
+ The user sends the request to start the game, as a response it receives the lower and upper bounds of the number to be predicted.
    + Request : /game/start
    + > {  
      &emsp; "userID" : [userID]  
      }
    + Response :  
      > {  
      &emsp; "userID" : [userID],  
      &emsp; "gameId : [gameID],  
      &emsp; "origin" : [lower bound],  
      &emsp; "bound" : [upper bound],	 
      }

+ The input, user provides should be a number within the bounds.
    + Request : /game/predict<br>
      > {  
      &emsp; "userId" : [userId],  
      &emsp; "gameId" : [gameId],  
      &emsp; "predictedNumber" : [number]  
      }
+ Response specifies whether the number is **HIGHER**, **LOWER**, **EQUAL** or **INVALID** 

## Game Process
+ User starts the game providing the user ID.
+ In response gets details about the bounds and attempts remaining.
+ User predicts the number within bounds.
+ Gets a response whether the number is higher lower or equal.
+ Game ends at 0 attempts or correct answer.
