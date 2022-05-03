### How to run?

1. in [gradle build script file](build.gradle.kts) paste inside "run" task in args token of your bot.
2. execute "gradle run"

### Bug 2: "FSM no answers"

### What sould I do?

1. User u1 writes the bot: /start. 

What happened?
* Bot jumps from null to AState for u1.
* Bot sends u1 "Hello A"

2. User u2 writes the bot: /start.

What happened?
* Bot jumps from null to AState for u2.
* Bot sends u2 "Hello A"

(2.1 User u2 can write the bot anything and correctly jump states)

3. User u1 writes the bot anything (for example "lol") to stay on AState

What happened?
* Bot does not answer u1 (lambda in strictlyOn<ASTate> not called) <<< **IT'S BUG**!

(optionally)
3+. u1 / u2 writes the bot anything to stay at AState.

What happened?
* Bot does not answer anybody! <<< **IT'S BUG**!

---
* BState here no matter
* Expected behaviour for 3: bot will send answer for u1