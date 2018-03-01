title: Kotlin
class: animation-fade
layout: true

<!-- This slide will serve as the base layout for all your slides -->
.bottom-bar[
  {{title}}
]

---

class: impact
background-image: url(logo.svg)

# {{title}}
## a _very_ short introduction


???

Kotlin is a programming language made by Jetbrains. The same people behind IntelliJ IDEA.
It was announced in 2011, and has been steadily been improved since then.
It is Open Source, Apache 2 lisenced statically typed language which compiles on the JVM,
Javascript and in beta Native.

Google announced on the lastest Google IO that Kotlin would be officially supported on Android.
Meaning there are now three official languages: Java, C++ and Kotlin.

The ecosystem is currently exapanding, but the interop story between Java and Kotlin is very good.
I have successfully used Java from Kotlin and Kotlin from Java.

Kotlin is by many considered a nice alternative to Java, but does not seek to replace it.

Enough with talk, lets look at some code.


---
# Declarations

Type of variables are declared with a `:` on the `right` hand side of the value.

## Variables

```kotlin
val foo: String = "meh"
foo = "bar" //compliation error
```

???

Vals are final variables which may not be reassigned.
Most often we dont declare types for var/val, but let the compiler figure out the type. This is known as type infererence.

---
# Declarations

Type of variables are declared with a `:` on the `right` hand side of the value.

## Variables

```kotlin
var foo: String = "meh"
foo = "bar" //OK
```

???
Vars are mutable variables which may be assigned

---
# Methods

## Single expression
```kotlin
 fun sayHello(who: String): String = "Hello, $who"
```

## Multiple expressions

```kotlin
 fun sayHello(who: String): String {
   println("we have a sideeffect")
   return "Hello, $who"
 } 
```

???

We are here using two different approaches. 
Since we have only a single expression, we can use the "=" sign to return the value.
We are also using String interpolation, so we can refer to the "who" value inside the string.


---

#Nullable types

```kotlin
var foo: String = "fooo"
foo = null //Does not compile
```

```kotlin
var foo: String? = "fooo"

foo = null
```

???
Here we have two declarations, one which is nullable.
The nice thing about that is that the language requires you to deal with the problem everywhere its used.
We will see more about how its used in the next presentation.

---

#Null guards

```kotlin
fun first(foo: Foo?): Int? {
    return foo?.bar?.baz
}

fun second(foo: Foo?): Int {
    return foo?.bar?.baz ?: 0
}
```

???
The first method there is requiring nulls to be checked everywhere. The returned value may also be null.

the second methods requires that we have a non-null value returned, so here we use a default or null, or the elvis operator to set a value if
everything evalutated to null.

---

# == that makes sense
Yes, it's an alias to Object.equals


```kotlin
val someString = ...
if (someString == "foo") "bar" else "other"
```

???

A bit much to unpack here. 
We check if the string is Object.equals 
---

# if else is an expression

```kotlin
val someString = ...
val s: String = if (someString == "foo") "bar" else "other"
```

???

No more Terniary! The last expression in the if-else body is the returned value.
---

# enums

```kotlin
enum class Player {
  One, Two
}
```

???
We have real Java enums
---

# data classes

```kotlin
data class Person(val name: String, val age: Int)
```

???
Generates Getters and setters, A correct equals and hashcode, and a nice toString.
We also get a copy method which we can change parts of the person and get a NEW person back.
---

# data classes

```kotlin
data class Person(val name: String, val age: Int)
val person = Person("Erlend", 37)

val older = person.copy(age = 38)

```

???
Older now contains myself but the age increased to 38.
We also see another feature which are called named parameters. 
This means that if we for instance have multiple arguments with the same type,
like string we can use the name for the param to distinqush them on the call site.
This feature only works in kotlin and not in Java.
---

# Sealed classes
```kotlin
sealed class State {
    object End : State()
    data class InProgress(val numMoves: Int): State()
}
```

???
We use sealed classes when we want to express types which are usually called Sum types.
In the example above we have a State which may be in two different states, one which
carries data (number of moves left in the game), and one which singals an End state.
---

# autocasting
```kotlin
sealed class State {
    object End : State()
    data class InProgress(val numMoves: Int): State()
}

val state = ...

if (state is InProgress) {
    doSomething(state.numMoves)
}
```

???
This checks if something is instance of something else, and inside the if block the state value is autocasted to InProgress.
---

# When expressions

Select statement on steriods.

```kotlin
fun stateToString(state: State): String = when(state) {
  is State.End -> "The game is complete"
  is State.InProgress -> "There are ${state.numMoves} left"
}

```
???
When statements are pretty cool.
When checking the object inside the when expression, it needs to be a boolean expression
when using the is operator we also can use the object state of the Inprogress type.
This is also known as autocasting, and we'll get back to that.
---

# Functions
```kotlin
val list = listOf("1", "2", "3")
val listOfInts = list.map{v -> Integer.parseIn(v)}
val listOfInts = list.map{Integer.parseInt(it)}
val listOfInts = list.map{it.toInt()}
val listOfInts = list.map(String::toInt)

```

???
Also a bit to unpack here.
map is an extension methods to java.util.List which will transform the list into a listOfInt
These are four different ways of defining functions.
The first is a "full" definition.
The seconds in using the fact that a with only one parameter gets the name "it". 
The third is using an extension method on String which is called "toInt"
the fourth is using the extension methods as a Method Reference.
---
# Cool stuff not time to talk about

* Extension methods
* Pair og Triple
* Operator overloading
* infix methods
* inline methods
* multiple classes in the same sourcefile
* local classes and methods
* delegation
* generics with declaration site variance
* coroutines
* properties (getters/setters)

???
There is simply too much to talk about for a 15 minute session. Please talk to me after if you want to know more.
---

class: impact
background-image: url(logo.svg)

# Thanks