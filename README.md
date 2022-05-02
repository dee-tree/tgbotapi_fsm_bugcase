### How to run?

1. in [gradle build script file](build.gradle.kts) paste inside "run" task in args token of your bot.
2. execute "gradle run"


### What sould I do?

1. User u1 writes the bot: /start. 

What happened?
* Bot jumps from null to AState for u1.
* Bot sends u1 "Hello A"

2. Account u2 writes the bot: /start.

What happened?
* Bot jumps from null to AState for u2.
* Bot sends u1 "Hello A"
* Bot sends u2 "Hello A"


3. You can write bot anything from both u1 and u2 (no matter) and get answer in AState for u1 and u2 
at the same moment

---
* BState here no matter
* Expected behaviour for 2: bot will send answer only for u2