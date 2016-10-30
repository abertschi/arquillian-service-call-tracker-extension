[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ch.abertschi.sct.arquillian/arquillian-service-call-tracker-extension-api/badge.svg?style=flat)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22ch.abertschi.sct.arquillian%22)
[![Build Status](https://travis-ci.org/abertschi/arquillian-service-call-tracker-extension.svg?branch=master)](https://travis-ci.org/abertschi/arquillian-service-call-tracker-extension) 
[![codecov](https://codecov.io/gh/abertschi/service-call-tracker/branch/master/graph/badge.svg)](https://codecov.io/gh/abertschi/service-call-tracker)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/55aa01d9bb474105afbde7bec70a62c0)](https://www.codacy.com/app/abertschi/arquillian-service-call-tracker-extension?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=abertschi/arquillian-service-call-tracker-extension&amp;utm_campaign=Badge_Grade)
[![Apache 2](http://img.shields.io/badge/license-APACHE2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

# service-call-tracker extension for JBoss Arquillian

This is the JBoss Arquillian extension for [service-call-tracker](https://github.com/abertschi/service-call-tracker).

## Getting started

Add these maven artifacts to your project:

```xml
<dependency>
	<groupId>ch.abertschi.sct.arquillian</groupId>
	<artifactId>arquillian-service-call-tracker-extension-api</artifactId>
	<version>VERSION</version>
</dependency>
<dependency>
	<groupId>ch.abertschi.sct.arquillian</groupId>
	<artifactId>arquillian-service-call-tracker-extension-impl</artifactId>
	<version>VERSION</version>
</dependency>
```
- service-call-tracker (ch.abertschi.sct:service-call-tracker-impl) is added to the Arquillian deployment programmatically and don't need to be set as a maven dependency.


### What this extension does:
- Configures service-call-tracker with information set in annotations placed on your Arquillian test methods
- Packes service-call-tracker storage files to your Arquillian deployment

Use `@RecordCall` and `@ReplayCall` annotations on your Arquillian test methods to configure service-call-tracker. 
The configuration set with these annotations is accessable with [`SctConfigurator.getInstance().getConfiguration()`](https://github.com/abertschi/service-call-tracker/blob/master/api/src/main/java/ch/abertschi/sct/api/SctConfigurator.java) and 
must be used to instanciate service-call-tracker.

```java
@RunWith(Arquillian.class)
public class SampleArquilianTest
{
    @Inject
    MyService service;

    @Deployment
    public static Archive<?> deploy()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(...)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @RecordCall("recordings.xml") // creates file recordings.xml with recorded calls
    public void test_around_aspect()
    {
       	// your test
    }
    
    @Test
    @ReplayCall("calls.xml") // replays calls stored in calls.xml located in current directory
    public void test_around_aspect()
    {
	// your test
    }
}
```

```java
// Create service-call-tracker
Configuration config = SctConfigurator.getInstance().getConfiguration(); // set by this Arquillian extension
ServiceCallTracker serviceCallTracker = new ServiceCallTracker(config);
```

### Arquillian.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<arquillian xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns="http://jboss.org/schema/arquillian"
           xsi:schemaLocation="http://jboss.org/schema/arquillian http://jboss.org/schema/arquillian/arquillian_1_0.xsd">

   <engine>
       <property name="deploymentExportPath">target/deployments</property>
   </engine>

   <extension qualifier="serviceCallTracker">
       <property name="recordingEnabled">true</property>
       <property name="replayingEnabled">true</property>

       <property name="throwExceptionOnNotFound">true</property>
       <property name="throwExceptionOnIncompatibleReturnType">true</property>

       <property name="sourceDirectory">./src/test/java</property>
       <property name="recordingStorageDirectory">./src/test/resources</property>
       <property name="replayingStorageDirectory">./src/test/resources</property>

   </extension>
</arquillian>
```

- `recordingEnabled`:
 - default: true
 - Enable the recording feature (activated with `@RecordCall` in code)
 
- `replayingEnabled`:
 - default: true
 - Enable the replaying feature (activated with `@ReplayCall` in code)

- `throwExceptionOnNotFound`:
 - default: true
 - Throw exception if replaying mode is activated but no matching call was found in storage file
 
- `throwExceptionOnIncompatibleReturnType`:
 - default: true
 - Throw exception if return type of intercepted method does not match with stored response. If set to `false`, service-call-tracker will execute method implementation instead.
 
- `sourceDirectory`:
 - default: `./src/main/java'
 - The root directory of your test soure code.

- `recordingStorageDirectory`:
 - default: `./'
 - The root directory to look for recording storage files. Storage files are first searched in the directory of your test class and then at this location.

- `replayingStorageDirectory`:
 - default: `./'
 - The root directory to look for replaying storage files. Storage files are first searched in the directory of your test class and then at this location.

### Big-Picture
 
![big picture](https://raw.githubusercontent.com/abertschi/service-call-tracker/master/bigpicture.png)


 


