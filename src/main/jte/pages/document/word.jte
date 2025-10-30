@param String id
@param String content
@param String className

@param boolean fx
@param String fxMethod = null
@param String fxAction = null
@param String fxTarget = null
@param String fxSwap = null
@param gg.jte.Content form = null

@if(fx)
    <div
    id="${id}"
    class="${className}"
    fx-method="${fxMethod != null ? fxMethod : ""}"
    fx-action="${fxAction != null ? fxAction : ""}"
    fx-target="${fxTarget != null ? fxTarget : ""}">
        ${content}
    </div>
@else
    <div
    id="${id}"
    class="${className}">
        <span>${content}</span>
        @if(form != null)
            ${form}
        @endif
    </div>
@endif
