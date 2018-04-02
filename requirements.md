# “Trolley Dilemma: The Game”—Requirements

## Components

### Game

#### Data

* points: `{ utils: 0, kantpoints: 0 }`
* dilemmas: [RTrolleyProblem]
* count: 0
* weights: [1]

#### Play

* create dilemma
* listen to decision
* print results
* count up
* update dilemma data
* quit?

### Dilemma

_Not to be invoked directly_

#### Data

* point change: `{ utils: 0, kantpoints: 0 }`
* decision message: ''
* entropy: 0.7

#### Methods

* print dilemma
* print decision
* update scores
* I/O
  * choice (plus validation)

### Abstract trolley

#### Data

* uppertrack: none
* lowertrack: none
* lowertracktext: ""
* uppertracktext: ""
* decisionmsg

#### Methods

* Print dilemma
* Print decision

### Real trolley problem

#### Data

* uppertrack: random(0, 5)
* lowertrack: random(1, 10)

### Fat man

#### Data

* workers: random(0, 4)

### Murderer liar

#### Data

* friends: random(2, 10)
* entropy: 0.1

### Harambe trolley

#### Data

* uppertrack: random(3, 20)
* lowertrack: 0.3
* entropy: 0.02

### Book trolley

#### Data

* entropy: 0.2
* bookutils: random(3, 10)

### Drowning child

#### Data

* clothes: random(500, 2000)
* children: random(0, 8)
* special: false
* entropy: 0.5
* if random(1, 5) == 1
  * special: true
  * entropy: 0.1

## Calculation rules

move != 1: lever pulled
move == 0: lever left

### Real trolley

#### utils

* move == -1 && uppertrack > 0
  * "You made the worst possible utilitarian choice."
  * "You are a moral monster. Lost %s utils" (lowertrack + uppertrack)

* diff < 0 && move == 1 || diff > 0 && move == 0
  * "You have made the wrong utilitarian decision. Lose "+ str(abs(diff)) + " utils!"
* lowertrack == uppertrack
  * "Your choice is neutral on utilitarian grounds"
* diff > 0 && move || diff < 0 && !move
  * "You have made the correct utilitarian decision and saved a net "+ str(abs(diff)) + " lives."
  * "Gain "+ str(abs(diff))+ ' utils!'

#### kantpoints

* move == 0
  * default
  * "The dilemma is not your problem. On Kantian grounds, that is enough."
  * "There is no change in your Kant points. #NotYourProblem."
* move == 1 && diff > 0
  * "You have a hypothetical imperative to save lives, but not a categorical one."
  * "Gain 1 Kant point"
* else
  * "You are a MURDERER who has violated the categorical imperative!"
  * "Lose 10 Kant points."

## Strings

* Count
  * You have encountered xxx ethical dilemmas."
* Quit
  * "Do you want to keep playing? [Yes/No] "
  * "Alright, let's keep going."
  * "Life is a series of trolley problems. You cannot avoid them."
* Kant default
  * "The dilemma is not your problem. On Kantian grounds, that is enough."
  * "There is no change in your Kant points. #NotYourProblem."
  * Kantpoints: 0
* Abstract trolley
  * "Do you pull the lever? [Yes/No] "
  * "A runaway trolley is barrelling towards " + self.lowertracktext
  * "You can pull a lever to divert the trolley to another track, containing " + self.uppertracktext
  * "You have pulled the lever."
  * "You let the lever be."