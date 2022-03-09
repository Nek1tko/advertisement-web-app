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
        // find the DropzoneArea node
        const dropzoneAreaWrapper = wrapper.find(DropzoneArea);
        // call its onChange prop
        const onChange = 'onChange';
        dropzoneAreaWrapper.prop(onChange)();
        // check handleDropzoneChange has been called
        expect(props.onChange).toHaveBeenCalled();

        // check getFileAddedMessage function
        const fileName = 'file.jpg';
        const message = "Файл " + fileName + " успешно загружен";
        expect(dropzoneAreaWrapper.prop('getFileAddedMessage')(fileName)).toBe(message);
    });
});
