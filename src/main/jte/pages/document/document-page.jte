@param String title
@param String content

@template.base(
    content = @`
        <div
        id="document-page">
            <div
            id="document">
                <span
                id="title">${title}</span>
                <div
                id="content">${content}</div>
            </div>
        </div>
    `
)