package(default_visibility = ["//biforst:__subpackages__"])

java_library(
    name = "lower_producer_fast_consumer_lib",
    srcs = glob(["*.java"]),
    deps = [
        "//3rdparty/jvm/com/lmax:disruptor",
        "//biforst/models/kotlin/vn/com/momo/clazz:biforst_clazz_lib",
        "//common-libs:common_lib_lite",
    ],
)

java_binary(
    name = "lower_producer_fast_consumer_lib_main",
    main_class = "vn.com.momo.fastproducerlowconsumer.LowProducerFastConsumerMain",
    runtime_deps = [
        ":lower_producer_fast_consumer_lib",
    ],
)
