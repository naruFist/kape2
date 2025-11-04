# _kape2_
## _kotlin api paper extesion v2_
[![java](https://img.shields.io/badge/java-21-ED8B00.svg?logo=java)](https://www.azul.com/)
[![kotlin](https://img.shields.io/badge/kotlin-2.2.21-585DEF.svg?logo=kotlin)](http://kotlinlang.org)
[![gradle](https://img.shields.io/badge/gradle-8.8-02303A.svg?logo=gradle)](https://gradle.org)
## upgrade version of [kape](https://github.com/naruFist/kape)

### _< Feature >_
- better code writing.
- only demanding functions.

### implement: [jitpack.io](https://jitpack.io/#naruFist/kape2)
**version : $V**
```gradle
repositories {
    maven { url = uri("https://jitpack.io") }
}
dependencies {
    implementation("com.github.naruFist:kape2:$V")
}
```

### Usage
```kotlin
class Example: JavaPlugin() {
    override fun onEnable() {
        Kape.plugin = this
        
        // Easy Listener Registration
        join()
        interact()
        ...
    }
}

// Listener Registration Functions
fun join() { // if player join, send 'Hi' (aqua color)
    Kape.listener<PlayerJoinEvent> { event ->
        event.player.sendMessage(text("Hi"), Color.AQUA)
    }
}

fun interact() {
    Kape.listener<PlayerInteractEvent> { event ->
        ...
    }
}
```
