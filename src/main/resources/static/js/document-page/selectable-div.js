class SelectableDiv extends HTMLDivElement {
    details = {};
    constructor() {
        super()
    }
    connectedCallback() {
        document.addEventListener('mouseup', () => {
            this.#releaseSelection()
        })
    }
    #releaseSelection() {
        const selection = window.getSelection()
        const anchorId = selection?.anchorNode?.parentElement?.id?.substring(1) ?? null
        const focusId = selection?.focusNode?.parentElement?.id?.substring(1) ?? null
        
        const range = (startId, from, to) => Array.from(
            { length: to - from + 1 },
            (_, i) => document.getElementById(`u${Number(startId) + i}`)
        )
        const selectedPhrase = range(anchorId, anchorId, focusId)
        const validate = (phrase) => {
            try {
                if(phrase.some(part => part === null)) {
                    throw new Error('phrase is not there!')
                }
                if(phrase.length < 2) {
                    throw new Error('phrase is too short!')
                }
                if(phrase.length > 5) {
                    throw new Error('phrase is too long!')
                }
                return { ok: phrase, err: undefined }
            }
            catch(e) {
                return { ok: undefined, err: e }
            }
        }
        const { ok, err } = validate(selectedPhrase)
        if(err) {
            return window.getSelection().removeAllRanges()
        }
        const rawContent = ok.map(part => part.innerText).join(' ')
        this.details = {
            startId: anchorId,
            endId: focusId,
            content: rawContent,
            rawContent: rawContent,
            selection: 'phrase'
        }
        const event = new Event('selectPhrase')
        document.getElementById('document-page').dispatchEvent(event)
        return window.getSelection().removeAllRanges()
    }
}
customElements.define('selectable-div', SelectableDiv, { extends: 'div' })