add_subdirectory(newpson/util)

create_javadoc(Demo
	PACKAGES newpson.util
	SOURCEPATH "${CMAKE_CURRENT_SOURCE_DIR}"
	CLASSPATH ${CMAKE_JAVA_INCLUDE_PATH}
	AUTHOR TRUE
	USE FALSE
	VERSION FALSE)

add_jar(Main
	PRIVATE Main.java
	MANIFEST MANIFEST.MF
	INCLUDE_JARS newpson/util/Container)
