export default {
    runtimeConfig(name) {
        return window.runtimeConfig && window.runtimeConfig[name] ? window.runtimeConfig[name] : process.env[name];
    }
}
