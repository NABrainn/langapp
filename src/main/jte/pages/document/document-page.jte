@import java.util.List
@import org.langapp.documents.dto.processor.Paragraph
@import org.langapp.documents.dto.processor.selection.SelectionStrategy

@param int documentId
@param String title
@param List<Paragraph> paragraphs
@param SelectionStrategy selection

@template.base(
    content = @`
    <script
    src="/js/document-page/selectable-div.js"></script>
    @template.pages.document.document-page-body(
        documentId = documentId,
        title = title,
        paragraphs = paragraphs,
        selection = selection
    )
    `
)