@import java.util.List
@import org.langapp.documents.dto.processor.Paragraph

@param String title
@param List<Paragraph> paragraphs

@template.base(
    content = @`
    <script
    src="/js/document-page/selectable-div.js"></script>
    @template.pages.document.document-page-body(
        title = title,
        paragraphs = paragraphs
    )
    `
)