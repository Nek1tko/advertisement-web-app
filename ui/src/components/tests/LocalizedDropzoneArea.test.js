import '../LocalizedDropzoneArea'
import LocalizedDropzoneArea from "../LocalizedDropzoneArea";
import Adapter from "@wojtekmaj/enzyme-adapter-react-17";
import {configure, mount, shallow} from 'enzyme';
import {DropzoneArea} from "material-ui-dropzone";

const props = {
    filesLimit: 3,
    maxFileSize: 3145728,
    acceptedFiles: [".jpeg", ".jpg"],
    onChange: jest.fn()
};

configure({adapter: new Adapter()});


describe('LocalizedDropZoneAreaTests', () => {
    test('LocalizedDropZoneAreaRenderTest', () => {
        mount(<LocalizedDropzoneArea{...props} />);
    });

    test('LocalizedDropZoneAreaSnapshotTest', () => {
        const wrapper = shallow(<LocalizedDropzoneArea{...props} />);
        expect(wrapper).toMatchSnapshot();
    });

    test('LocalizedDropZoneAreaOnChangeTest', () => {
        const wrapper = shallow(<LocalizedDropzoneArea{...props} />);
        const dropzoneAreaWrapper = wrapper.find(DropzoneArea);
        const onChange = 'onChange';
        dropzoneAreaWrapper.prop(onChange)();
        expect(props.onChange).toHaveBeenCalled();
    });

    test('LocalizedDropZoneAreaGetFileAddedMessageTest', () => {
        const wrapper = shallow(<LocalizedDropzoneArea{...props} />);
        const dropzoneAreaWrapper = wrapper.find(DropzoneArea);
        const fileName = 'file.jpg';
        const message = "Файл " + fileName + " успешно загружен";
        expect(dropzoneAreaWrapper.prop('getFileAddedMessage')(fileName)).toBe(message);
    });

    test('LocalizedDropZoneAreaGetFileLimitExceedMessageTest', () => {
        const wrapper = shallow(<LocalizedDropzoneArea{...props} />);
        const dropzoneAreaWrapper = wrapper.find(DropzoneArea);
        const filesLimit = 3;
        const message = "Достигнуто максимальное количество файлов: " + filesLimit;
        expect(dropzoneAreaWrapper.prop('getFileLimitExceedMessage')(filesLimit)).toBe(message);
    });

    test('LocalizedDropZoneAreaGetFileRemovedMessageTest', () => {
        const wrapper = shallow(<LocalizedDropzoneArea{...props} />);
        const dropzoneAreaWrapper = wrapper.find(DropzoneArea);
        const fileName = 'file.jpg';
        const message = "Файл " + fileName + " удален";
        expect(dropzoneAreaWrapper.prop('getFileRemovedMessage')(fileName)).toBe(message);
    });

    test('LocalizedDropZoneAreaGetDropRejectMessageTest', () => {
        const wrapper = shallow(<LocalizedDropzoneArea{...props} />);
        const dropzoneAreaWrapper = wrapper.find(DropzoneArea);
        const rejectedFile = new File(["foo"], 'file.jpg');
        const acceptedFile = 'ignored.jpg';
        const maxFileSize = 3;
        const message = "Файл " + rejectedFile.name + " отклонен. Файл не поддерживается или размер файла больше "
            + (maxFileSize / 1024 ** 2) + " МБ";
        expect(dropzoneAreaWrapper.prop('getDropRejectMessage')(rejectedFile, acceptedFile, maxFileSize)).toBe(message);
    });
});
