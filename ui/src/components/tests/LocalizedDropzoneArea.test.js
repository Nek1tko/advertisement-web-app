/**
 * @group unit
 */

import '../LocalizedDropzoneArea'
import LocalizedDropzoneArea from "../LocalizedDropzoneArea";
import { mount, shallow } from 'enzyme';
import { DropzoneArea } from "material-ui-dropzone";

const props = {
    filesLimit: 3,
    maxFileSize: 3145728,
    acceptedFiles: [".jpeg", ".jpg"],
    onChange: jest.fn()
};

describe('LocalizedDropZoneAreaTests', () => {
    const wrapper = shallow(<LocalizedDropzoneArea{...props} />);
    const dropzoneAreaWrapper = wrapper.find(DropzoneArea);

    test('LocalizedDropZoneAreaRenderTest', () => {
        mount(<LocalizedDropzoneArea{...props} />);
    });

    test('LocalizedDropZoneAreaSnapshotTest', () => {
        expect(wrapper).toMatchSnapshot();
    });

    test('LocalizedDropZoneAreaOnChangeTest', () => {
        const onChange = 'onChange';
        dropzoneAreaWrapper.prop(onChange)();
        expect(props.onChange).toHaveBeenCalled();
    });

    test('LocalizedDropZoneAreaGetFileAddedMessageTest', () => {
        const fileName = 'file.jpg';
        const message = "Файл " + fileName + " успешно загружен";
        expect(dropzoneAreaWrapper.prop('getFileAddedMessage')(fileName)).toBe(message);
    });

    test('LocalizedDropZoneAreaGetFileLimitExceedMessageTest', () => {
        const filesLimit = 3;
        const message = "Достигнуто максимальное количество файлов: " + filesLimit;
        expect(dropzoneAreaWrapper.prop('getFileLimitExceedMessage')(filesLimit)).toBe(message);
    });

    test('LocalizedDropZoneAreaGetFileRemovedMessageTest', () => {
        const fileName = 'file.jpg';
        const message = "Файл " + fileName + " удален";
        expect(dropzoneAreaWrapper.prop('getFileRemovedMessage')(fileName)).toBe(message);
    });

    test('LocalizedDropZoneAreaGetDropRejectMessageTest', () => {
        const rejectedFile = new File(["foo"], 'file.jpg');
        const acceptedFile = 'ignored.jpg';
        const maxFileSize = 3;
        const message = "Файл " + rejectedFile.name + " отклонен. Файл не поддерживается или размер файла больше "
            + (maxFileSize / 1024 ** 2) + " МБ";
        expect(dropzoneAreaWrapper.prop('getDropRejectMessage')(rejectedFile, acceptedFile, maxFileSize)).toBe(message);
    });
});
