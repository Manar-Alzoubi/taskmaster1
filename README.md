# taskmaster1

* the task master app has three pages:
* Main page (My Tasks contains: title "My Task", picture, 2 buttons(add task, all tasks))

![main activity page](screenShots/main.jpg)


* add Task page (contains title, and some data to write: task title and description, button to submit and  text: total tasks)


![add task page](screenShots/add.jpg)


* all tasks page (contains image and back button)

![all tasks page](screenShots/all.jpg)

------------------------------------------------------------------
# Lab 27
* Modifid home page (contains settings button ,username appeared , 3 tasks buttons, add task button ,all task button)

![home page](screenShots/home.jpg)

* settings page (add a username to make it appear in home page)

![settings page](screenShots/settings.jpg)


-------------------------------------------------------
# lab 28

![Recycler view](screenShots/recyclerview.jpg)


![Task details](screenShots/detTask.jpg)

--------------------------------------------------

# Lab29

* add new Task and save it to database
  

  ![add new Task and save it to database](screenShots/addToDB.jpg)



* view (retrive) the task from Database
  

  ![view (retrive) the task from Database](screenShots/viewOnRecycleView.jpg)


  
* click on the task to view the details (title, description and state)
  

  ![task details](screenShots/viewTaskDet.jpg)
  

----------------------------------------------------------------------------

# Lab 31 
* Espresso test functions added to the the app :
    * assert that important UI elements are displayed on the page
    * tap on a task, and assert that the resulting activity displays the name of that task
    * edit the user’s username, and assert that it says the correct thing on the homepage
    * add task to the recycler view (added to database and viewed in the main page)
    
* screen Shoots
* Main page  
  
  ![Main page](screenShots/viewOnRecycleView.jpg)


* addTask page
  
  ![add-task page](screenShots/addToDB.jpg)


* all tasks page (contains image and back button)

![all tasks page](screenShots/all.jpg)


* details page

![task details](screenShots/viewTaskDet.jpg)


--------------------------------------
# Lab 32 
*  an AWS account created and the Amplify CLI installed 
*  create a Task resource that replicates our existing Task schema.Update all references to the Task data to instead use AWS Amplify to access your data in DynamoDB instead of in Room.


* Main page

  ![Main page](screenShots/awsRecy.jpg)



* details page

![task details](screenShots/DetAWS.jpg)


* AWS Tasks page

  ![add-task page](screenShots/TasksOnAWS.jpg)
 ---------------------------------------------------
# Lab 33
* 3 Teams added to the database and each user assigned to a Team
* Tasks that added also assigned to a specific Team
* when a user entered his/her data (username and Team ) the tasks for that Team will appeare in the homepage 

![add task team](screenShots/addTeamTask.jpg)



![Team1](screenShots/team1.jpg)


![Tasks for team 1](screenShots/tasksTeam1.jpg)


![AWS tasks and Teams](screenShots/teamAWS.png)

-------------------------------------------------------
 # Lab 34 : 

* app-release-APK is generated in this lab : used in publishing the app in play store 

--------------------------------------------
# Lab 36
* SignUp page 

![SignUp](screenShots/signup.jpg)


* Login page 

![Login](screenShots/login.jpg)


* logout button and display user info 

![logout](screenShots/entered.jpg)
