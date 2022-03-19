module.exports = {
    moduleFileExtensions: ["js", "jsx"],
    moduleDirectories: ["node_modules", "bower_components", "shared"],

    moduleNameMapper: {
        "\\.(css|less|scss|sass)$": "identity-obj-proxy",
    },
    setupFilesAfterEnv: ["<rootDir>/src/setupTests.js"],
    transformIgnorePatterns: [
        "node_modules[/\\\\](?!@amcharts[/\\\\]amcharts4)"
    ],
    collectCoverage: true
}
