cmake_minimum_required(VERSION 3.4.1)

add_library(hide_key SHARED
            hide_key.c)

# Include libraries needed for hide_key lib
target_link_libraries(hide_key
                      android
                      log)
