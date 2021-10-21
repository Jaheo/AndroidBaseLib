# AndroidBaseLib
Some commonly used basic functional classes of android

#### Step 1. Add the JitPack repository to your build file
``
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
``

#### Step 2. Add the dependency
``
    dependencies {
        implementation 'com.github.Jaheo:AndroidBaseLib:0.0.1'
    }
``