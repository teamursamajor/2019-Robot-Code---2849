# 2019-Robot-Code---2849
2019 Ursa Major 2849 Welcome to the 2019 Programming Conglomeration GitHub Repository, this is where all of our code will be placed.
***THIS README IS NOT FULLY UPDATED FOR VSCODE***

####Git Quick Reference for team members
We are going to be using VSCode this year instead of Eclipse. This is the first year we are using it, as it has just become FIRST’s officially supported IDE this year. This guide will cover how to go from nothing to setting up the entire work environment.

####Getting Started
Git Bash:

This program allows you to utilize Git for your projects through a command line. You can install it at https://git-scm.com/downloads. Tutorials on how to use Git Bash are further below.

To use Git Bash in the VSCode terminal, open VSCode and open the command window by pressing CTRL + Shift + P. Search for "Select Default Shell" and select Git Bash.

2019 NI Update Suite:

https://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/480793-offline-installation-preparation

Install by following the link. It will ask you to log in, so use teamursamajor@gmail.com and our regular password to get the download link. Install the file, extract it, open the folder, and run the setup application.

VSCode and WPILib:

https://github.com/wpilibsuite/allwpilib/releases

Follow the link and install the WPILib installer that corresponds to your OS. Run the installer, then click Select/Download VSCode. Click Download. Once that’s complete, make sure all checkboxes are checked (unless you have already installed 2019 WPILib software on this machine and the software unchecked them automatically), then click Execute Install. Don't forget to set Git Bash as the default shell and install the NavX libraries in VSCode (see those respective sections for more info).

NavX-MXP:

https://pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/

We use a sensor called the NavX MXP, which requires some outside libraries. Open the link and download the latest build of the libraries. After installing, run the setup program. Then, open VSCode and press CTRL + Shift + P to show all commands. Search
for "Manage Vendor Libraries," then "Install new libraries (offline)," then click on the NavX libraries.

JRE (Java Runtime Environment): 

If Strings or other basic objects are throwing errors, you are missing your JRE. Install it from https://java.com/en/download/manual.jsp

####Pulling Changes (Incomplete)

When you start your work for the day, always be sure to Pull, which adds changes made on other computers to your project.

Try not to make any changes before Pulling, as this will create a merge situation which can be stressful.

To pull, navigate to the Source Control tab on the left side menu (Looks like a weird Y) and click on the ellipses (“…”). Then click pull.

If there are any issues which require a merge, go to the ####Merge section

#####Committing and Pushing Changes (Incomplete)

After editing a file (or adding a file) you can see it under the Source Control tab on the left side menu (The one that looks like a weird Y). Changed or added files will be listed under the Changes tab, indicating you have an uncommitted change.
Committing changes is essentially a hard save that also adds information about when the change was made as well as exactly what changes were made.

To commit a change, you must first stage the changes to be committed. Luckily, this is very easy. Simply hover over the file and click on the “+” to stage it. If a file is under “Staged Changes” then it WILL be committed. If a file is under “Changes” then it will NOT be committed. 

In the Commit Message box, write a useful message that explains the changes you made. Appropriate messages would look like: "Fixed errors in Robot.java. Added Drive.java. Removed unnecessary code from Arm.java."
After writing your message, you can press CTRL + Enter or press the check mark above the box to commit. Committing alone does NOT push changes to the GitHub repository.

To push, click on the ellipses (“…”) in the top right corner of the Source Control tab, and click Push.
Commit often, push when you want others to use your work, and always Commit and Push at the end of the day.

#####Merging (INCOMPLETE)

Merging occurs when your committed changes do not match the committed changes pulled down from GitHub. When you Pull and there is a conflict, VSCode will alert you 

The files with the red circle need to have conflicts resolved. Open the file and look for the mess of >>>>>>>>>>>>>>> and various other text that Eclipse shows as errors. Keep the code you want to commit and delete the code and text that you do not want. If you are not sure if some code is necessary, ask around or use your best intuition. If you make the wrong decision, Git allows us to reclaim any lost code, so don't worry too much.

There are special programs called Merge Tools that will handle this process much better, but they will not be covered here. I recommend Meld if you want to try one.

####Using Git Bash

The above guides on using Git are specifically through VSCode’s integration with Git. There are also ways to use Git from the command line, which is why we downloaded Git Bash. This often allows for greater control and ease of access in complicated merges or other such scenarios.

####Add repo to your computer

In the command line, type

git clone https://github.com/teamursamajor/2019-Robot-Code---2849

This clones the 2019 Robot Code repository to your computer, Git will now track any changes you make to existing files.

####Adding files 

If you created a new file or changed a file, such as "Drive.java", you will need to tell Git to track this change. Simply type

git add Drive.java

replacing Drive.java with the file you want to add, Git will now "stage" that change.

####Deleting files 

If you want to remove a file from the repository, type

git rm Drive.java

or whatever file you want to delete. This deletes the file from your computer as well.

####Checking your files 

Typing

git 

will tell you the status of your files, such as what has been added, modified, or deleted. Files under "Changes to be committed:" will be committed to the repository, while files under "Changes not staged for commit:" will need to be added before committing to make the changes permanent.

####Committing:

Your code needs to be committed after you have made changes. Otherwise, the changes will only stay on your computer and not be added to the online repository. 
Typing

git commit

will launch a text editor and allow you to add a message to your commit. The commit message should describe the changes you made, and if you leave it blank, it will not commit. If you want to skip opening the editor, typing

git commit -m [message]

where [message] is your message will allow you to add a message without opening the editor. You can also skip the "add" command with

git commit -a

which will automatically add all files that have been changed. It will NOT add files that have been created. To add all content modified/created/removed (potentially dangerous, be aware when using) use the command

git add -A

####Pushing: 

After committing, you must push your code to the repository to save your changes. Simply type

git push

and you will be prompted for your username and password. Use teamursamajor's username and password.

Good practice is to commit often and push your commits at the end of the day. This is your responsibility. Sign your commits with your name or your programming name at the end of each commit.

####Hardware Overview

If you’re ever confused about the function of any hardware on the eboard, look at https://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/599672-frc-control-system-hardware-overview
