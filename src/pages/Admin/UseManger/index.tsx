import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { ProTable, TableDropdown } from '@ant-design/pro-components';
import { useRef } from 'react';
import {search} from "@/services/ant-design-pro/api";
import {Image} from "antd";


const columns: ProColumns<API.CurrentUser>[] = [
  {
    dataIndex: 'index',
    valueType: 'indexBorder',
    width: 48,
  },
  {
    title: '用户名',
    dataIndex: 'userAccount',
    copyable: true,
    ellipsis: true,
    tip: '用户过长会自动收缩',
  },
  {
    title: '头像',
    dataIndex: 'imgUrl',
    copyable: true,
    // record 这个头像这一条数据，ReactNode 表示整行数据
    // 取出是字符串，要用img属性渲染
    render: (a, record) => (
        <div>
          <Image src={record.imgUrl} alt="" width={70} />
        </div>

    )
  },
  {
    title: '性别',
    dataIndex: 'sex',
    copyable: true,
  },
  {
    title: '手机号',
    dataIndex: 'phone',
    copyable: true,
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    copyable: true,
  },
  {
    title: '管理员',
    dataIndex: 'isAdmin',
    // valueType 定义一些枚举值 ,status表示颜色
    valueType: 'select',
    valueEnum: {
      0: {text: '普通用户',status:'default'},
      1: {text: '管理员',status:'success'}
    }
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    valueType: 'dateTime',
  },
  {
    title: '操作',
    valueType: 'option',
    key: 'option',
    render: (text, record, _, action) => [
      <a
        key="editable"

        onClick={() => {
          console.log(record)
          // @ts-ignore
          action?.startEditable?.(record.id);
        }}
      >
        编辑
      </a>,
      <a href={record.imgUrl} target="_blank" rel="noopener noreferrer" key="view">
        查看
      </a>,
      <TableDropdown
        key="actionGroup"
        onSelect={() => action?.reload()}
        menus={[
          { key: 'delete', name: '删除' },
        ]}
      />,
    ],
  },
];



export default () => {
  const actionRef = useRef<ActionType>();



  return (
    <ProTable<API.CurrentUser>
      columns={columns}
      actionRef={actionRef}
      cardBordered


      // @ts-ignore
      request={async (
        /* eslint-disable-next-line @typescript-eslint/no-unused-vars */
        params = {}, sort, filter
      ) => {
        // console.log(sort, filter);
        const userList =  await search()
        return {
          data: userList
        }
      }}
      editable={{
        type: 'multiple',
      }}
      columnsState={{
        persistenceKey: 'pro-table-singe-demos',
        persistenceType: 'localStorage',
        onChange(value) {
          console.log('value: ', value);
        },
      }}
      rowKey="id"
      search={{
        labelWidth: 'auto',
      }}
      form={{
        // 由于配置了 transform，提交的参与与定义的不同这里需要转化一下
        syncToUrl: (values, type) => {
          if (type === 'get') {
            return {
              ...values,
              created_at: [values.startTime, values.endTime],
            };
          }
          return values;
        },
      }}
      pagination={{
        pageSize: 5,
        onChange: (page) => console.log(page),
      }}
      dateFormatter="string"
      headerTitle="高级表格"
    />
  );
};
