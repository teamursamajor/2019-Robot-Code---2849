# 2019-Robot-Code---2849
2019 Ursa Major 2849 Welcome to the 2019 Programming Conglomeration GitHub Repository, this is where all of our code will be placed.

*This README should now be fully updated for the 2019 season. Please contact Hershal through GroupMe if something is missing, needs clarification, or contains errors.*

#### Git Quick Reference for team members
We are going to be using VSCode this year instead of Eclipse. This will be our first year using it, as it has just become the official FIRST idea this year. This guide will cover how to go from nothing to setting up the entire work environment.

#### Getting Started
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

#### Using Git Bash
While VSCode does have built-in integration with Git, we can also use Git from the command line or terminal (aka Git Bash). This allows for greater control and ease of access and is generally preferable. If you followed the above installation guides, then you should also have Git Bash accessable through the VSCode terminal, which is really nice.

#### Add the repo to your computer

In the command line, type

git clone https://github.com/teamursamajor/2019-Robot-Code---2849

This clones the 2019 Robot Code repository to your computer, Git will now track any changes you make to existing files.

####Adding files 

If you created a new file or changed a file, such as "Drive.java", you will need to tell Git to track this change. For example, simply type

git add Drive.java

replacing Drive.java with the file you want to add, Git will now "stage" that change. You can also add multiple files in the same line.

Ex: git add Drive.java Lift.java Intake.java

would add all three files.

#### Deleting files 

If you want to remove a file from the repository, type

git rm Drive.java

or whatever file you want to delete. **This deletes the file from your computer as well.**

#### Checking your files 

Typing

git status

will tell you the status of your files, such as what has been added, modified, or deleted. Files under "Changes to be committed:" will be committed to the repository, while files under "Changes not staged for commit:" will need to be added before committing to make the changes permanent.

You can also check commit history by typing

git log

and it will bring up a list of prior commits, commit messages, and the author/date. Press "q" to leave the list and return to the command line.

#### Committing:

After editing a file (or adding a file) your code needs to be committed. Otherwise, the changes will only stay on your computer and not be added to the online repository. 
Typing

git commit

will launch a text editor and allow you to add a message to your commit. The commit message should describe the changes you made, and if you leave it blank, it will not commit. If you want to skip opening the editor, typing

git commit -m [message]

where [message] is your message will allow you to add a message without opening the editor. Write a useful message that explains the changes you made. Appropriate messages would look like: "This commit will fix errors in Robot.java, add Drive.java, and remove unnecessary code from Arm.java." You can also skip the "add" command with

git commit -a

which will automatically add all files that have been changed. It will NOT add files that have been created. To add all content modified/created/removed use the command

git add -A

(*Potentially dangerous; be aware when using it. You may end up adding user-specific files that we do not want on the repository*)

Commit often, push when you want others to use your work, and always Commit, Pull, then Push at the end of the day.

#### Pulling:

When you start your work for the day, always be sure to Pull immediately before doing anything. This adds changes made on other computers to your project. 

Try not to make any changes before Pulling, as this will create a merge situation which can be stressful.

To pull, type

git pull

and git will automatically add changes to your local project. If any issues require a merge, to the ###Merge section.

#### Pushing: 

After committing, you must push your code to the repository to save your changes. Simply type

git push

and you will be prompted for your username and password. *Use teamursamajor's username and password.*

Good practice is to commit often and push your commits at the end of the day. This is your responsibility. *Sign your commits with your name or your programming name at the end of each commit.*

#### Merge

When you have edited and commited code which has also been changed on the repository, you will run into a merge conflict (occurs after you commit code then pull changes).

Merge conflicts are recognized by VSCode. Differences are highlighted and there are inline actions that allow you to accept current changes, accept incoming changes, accept both, or compare both. Look for the mess of >>>>>, =====, <<<<<, and various other text/red lines. Head refers to your local changes and Test refers to the incoming changes from the repo. 

Keep the code you want to commit and delete any code or text you do not want. If you're unsure if some code is necessary, ask others or use your intuition. If you make the wrong decision, we can use Git to reclaim any lost code, so don't worry about it. (Yay for version control!)

There are special programs called Merge Tools that will handle this process much better, but they will not be covered here. I recommend Meld if you want to try one.

#### Using Git in VSCode

Now, generally you should always use Git Bash and the terminal for your version control needs. However, **after** you have learned to use all of the commands above, you can use the VSCode integration with Git for convenience.

First, look at the menu on the left side of VSCode. Click on the third option (the one that looks like a "Y"). This is the source control menu. It will show you any changes that you have made while editing. These changes are not yet staged for commit. To do so, hover over them and click on the "+." You can also click on the first icon to open the file in the editor or the second icon to discard changes (**be very careful so you don't accidentally discard all the work you just did**). You can also hover over the "Changes" bar and click on the "+" to stage all files for commit.

Next, you can type the commit message in the box above it, then press CTRL + Enter to commit it. You can also click on the check mark to commmit. If you need to perform any other action (like pull, push, undo last commit, etc...) click on the ellipse ("...") and select the option you need from the drop down menu.

If you need to refresh so that VSCode recognizes new changes, just click on the Refresh icon in the middle of the bar. (**Do NOT press F5; in VSCode F5 will attempt to run and debug your code, not refresh!**)

#### Hardware Overview

If you’re ever confused about the function of any hardware on the eboard, look at https://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/599672-frc-control-system-hardware-overview
