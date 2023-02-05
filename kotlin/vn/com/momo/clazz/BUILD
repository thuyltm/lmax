package(default_visibility = ["//biforst:__subpackages__"])

java_library(
    name = "biforst_clazz_lib",
    srcs = glob([
        "LongEvent.java",
        "LongEventFactory.java",
        "LongEventHandler.java",
        "LongEventProducer.java",
    ]),
    deps = [
        "//3rdparty/jvm/com/lmax:disruptor",
        "//common-libs:common_lib_lite",
    ],
)

java_binary(
    name = "circular_buffer_main",
    main_class = "vn.com.momo.clazz.CircularBufferMain",
)
