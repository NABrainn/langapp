@param String id
@param String content
@param String className
@param String bgc = null

<div
id="${id}"
class="${className}"
data-bgc="${bgc != null ? bgc : ""}">
    ${content}
</div>
