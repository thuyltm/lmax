package(default_visibility = ["//biforst:__subpackages__"])

java_library(
    name = "consumer_dependencies_graph_lib",
    srcs = glob(["*.java"]),
    deps = [
        "//3rdparty/jvm/com/lmax:disruptor",
        "//biforst/models/kotlin/vn/com/momo/clazz:biforst_clazz_lib",
        "//common-libs:common_lib_lite",
    ],
)

java_binary(
    name = "consumer_dependencies_graph_main",
    main_class = "vn.com.momo.consumerdependenciesgraph.ConsumerDependenciesGraphMain",
    runtime_deps = [
        ":consumer_dependencies_graph_lib",
    ],
)
