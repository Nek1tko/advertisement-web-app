import { DropzoneArea } from 'material-ui-dropzone';

const getFileLimitExceedMessage = (filesLimit) => {
    return "Достигнуто максимальное количетсов файлов: " + filesLimit;
};

const getFileAddedMessage = (fileName) => {
    return "Файл " + fileName + " успешно загружен"
};

const getFileRemovedMessage = (fileName) => {
    return "Файл " + fileName + " удален";
};

const getDropRejectMessage = (
    rejectedFile,
    acceptedFiles,
    maxFileSize
) => {
    return "Файл " + rejectedFile.name + " отклонен. Файл не поддерживается или размер файла больше "
        + (maxFileSize / 1024 ** 2) + " МБ";
};


export default function LocalizedDropzoneArea(props) {
    return (
        <DropzoneArea
            filesLimit={props.filesLimit}
            maxFileSize={props.maxFileSize} // 3 mb
            acceptedFiles={props.acceptedFiles}
            onChange={props.onChange}
            dropzoneText="Перетащите сюда картинку или нажмите"
            getFileLimitExceedMessage={getFileLimitExceedMessage}
            getFileAddedMessage={getFileAddedMessage}
            getFileRemovedMessage={getFileRemovedMessage}
            getDropRejectMessage={getDropRejectMessage}
        >
        </DropzoneArea>
    );
}
