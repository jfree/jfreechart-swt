JFreeChart-SWT
==============

Version 1.1.0 - not yet released.

(C)opyright 2006-present, by David Gilbert and Contributors. All rights reserved.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.jfree/jfreechart-swt/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.jfree/jfreechart-swt)

Overview
--------
**JFreeChart-SWT** is a library that provides extensions for [JFreeChart](https://github.com/jfree/jfreechart) that allows charts
to be rendered in SWT applications. This code was originally developed as part of the JFreeChart project. It is now a standalone project.

Using JFreeChart-SWT
--------------------
To include JFreeChart-SWT in your own application, add the following dependency to your build tool:

    <dependency>
      <groupId>org.jfree</groupId>
      <artifactId>jfreechart-swt</artifactId>
      <version>1.0</version>
    </dependency>

License
-------
JFreeChart-SWT is free software under the terms of the GNU Lesser General Public License (LGPL) version 2.1 or later.

Please note that JFreeChart-SWT is distributed WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. Please refer to the license for details.

Change History
--------------

Version 1.1.0 (not yet released)
- add `fillZoomRectangle` ([#4](https://github.com/jfree/jfreechart-swt/pull/4))
- update [JFreeChart](https://github.com/jfree/jfreechart) dependency to version 1.5.6
- update [SWTGraphics2D](https://github.com/jfree/swtgraphics2d) dependency to version 1.1.0

Version 1.0 (20 February 2016)
- initial release as a standalone project (previously included with JFreeChart SWT support).