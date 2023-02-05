package(default_visibility = ["//biforst:__subpackages__"])

java_library(
    name = "batchrewind_lib",
    srcs = glob(["*.java"]),
    deps = [
        "//3rdparty/jvm/com/lmax:disruptor",
        "//biforst/models/kotlin/vn/com/momo/clazz:biforst_clazz_lib",
        "//common-libs:common_lib_lite",
    ],
)

java_binary(
    name = "batchrewind_main",
    main_class = "vn.com.momo.batchrewind.BatchRewindMain",
    runtime_deps = [
        ":batchrewind_lib",
    ],
)
