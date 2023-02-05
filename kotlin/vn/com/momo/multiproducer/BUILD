package(default_visibility = ["//biforst:__subpackages__"])

java_library(
    name = "multiproducer_lib",
    srcs = glob(["*.java"]),
    deps = [
        "//3rdparty/jvm/com/lmax:disruptor",
        "//biforst/models/kotlin/vn/com/momo/clazz:biforst_clazz_lib",
        "//common-libs:common_lib_lite",
    ],
)

java_binary(
    name = "multiproducer_main",
    main_class = "vn.com.momo.multiproducer.MultiProducerMain",
    runtime_deps = [
        ":multiproducer_lib",
    ],
)
