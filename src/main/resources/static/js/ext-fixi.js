document.addEventListener("fx:after", (evt)=>{
    if (evt.target.hasAttribute("ext-fx-push")){
        history.replaceState({fixi:true, url:location.href}, "", location.href)
        history.pushState({fixi:true, url:evt.detail.cfg.response.url}, "", evt.detail.cfg.response.url)
    }
})
window.addEventListener("popstate", async(evt)=>{
    if (evt.state.fixi){
        let historyResp = await fetch(evt.state.url)
        document.documentElement.innerHTML = await historyResp.text()
        document.dispatchEvent(new CustomEvent("fx:process"))
    }
})

document.addEventListener("fx:init", (evt) => {
    const elt = evt.target;
    if (elt.matches("[ext-fx-vals]")) {
        document.addEventListener("fx:config", (evt) => {
            const cfg = evt.detail.cfg;
            const valsAttr = elt.getAttribute("ext-fx-vals");
            let vals;
            if (valsAttr.startsWith("js:")) {
                vals = new Function("return " + valsAttr.slice(3))();
            } else {
                vals = new Function("return " + valsAttr)();
            }
            if (typeof vals !== "object" || vals === null || Array.isArray(vals)) {
                console.error("ext-fx-vals attribute did not evaluate to a valid object:", vals);
                return;
            }
            for (let key in vals) {
                if (typeof key === "string" && key.trim() === "") {
                    continue;
                }
                cfg.body.append(key, vals[key]);
            }
        });
    }
});