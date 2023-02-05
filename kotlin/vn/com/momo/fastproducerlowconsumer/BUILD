package(default_visibility = ["//biforst:__subpackages__"])

java_library(
    name = "fast_producer_low_consumer_lib",
    srcs = glob(["*.java"]),
    deps = [
        "//3rdparty/jvm/com/lmax:disruptor",
        "//biforst/models/kotlin/vn/com/momo/clazz:biforst_clazz_lib",
        "//common-libs:common_lib_lite",
    ],
)

java_binary(
    name = "fast_producer_low_consumer_lib_main",
    main_class = "vn.com.momo.fastproducerlowconsumer.FastProducerLowConsumerMain",
    runtime_deps = [
        ":fast_producer_low_consumer_lib",
    ],
)
