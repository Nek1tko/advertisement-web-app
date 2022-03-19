module.exports = {
    presets: [
        ["@babel/preset-env", {modules: "auto"}],
        ["@babel/preset-react", {runtime: "automatic"}],
    ],
    env: {
        test: {
            plugins: ["@babel/plugin-transform-modules-commonjs"]
        }
    }
}
